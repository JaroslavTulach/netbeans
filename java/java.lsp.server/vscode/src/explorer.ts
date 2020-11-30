import * as vscode from 'vscode';
import { LanguageClient } from 'vscode-languageclient';
import { NodeInfoRequest, NodeQueryRequest } from './protocol';

class VisualizerProvider implements vscode.TreeDataProvider<Visualizer> {
  private root: Promise<Visualizer[]>;

  constructor(
    private client: LanguageClient,
    id : string
  ) {
    this.root = new Promise((resolve) => {
      client.sendRequest(NodeInfoRequest.init, id).then((node) => {
        resolve([ new Visualizer(node) ]);
        this._onDidChangeTreeData.fire();
      });
    });
  }

  private _onDidChangeTreeData: vscode.EventEmitter<Visualizer | undefined | null | void> = new vscode.EventEmitter<Visualizer | undefined | null | void>();
  readonly onDidChangeTreeData: vscode.Event<Visualizer | undefined | null | void> = this._onDidChangeTreeData.event;

  refresh(v : Visualizer): void {
    this._onDidChangeTreeData.fire(v);
  }

  getTreeItem(element: Visualizer): vscode.TreeItem {
    return element;
  }

  getChildren(element?: Visualizer): Thenable<Visualizer[]> {
    if (element) {
      return this.client.sendRequest(NodeInfoRequest.children, element.data.id).then(async (arr) => {
        let res = Array<Visualizer>();
        for (let i = 0; i < arr.length; i++) {
          let d = await this.client.sendRequest(NodeInfoRequest.info, arr[i]);
          let v = new Visualizer(d);
          v.parent = element;
          res.push(v);
        }
        return res;
      });
    } else {
      return this.root;
    }
  }
}

class Visualizer extends vscode.TreeItem {
  constructor(
    public data : NodeInfoRequest.Data
  ) {
    super(data.displayName, data.leaf ? vscode.TreeItemCollapsibleState.None : vscode.TreeItemCollapsibleState.Collapsed);
    this.id = data.name;
    this.description = data.shortDescription;
  }
  parent: Visualizer | null = null;
  contextValue = "node";
}

export function register(c : LanguageClient) {
  c.onRequest(NodeQueryRequest.type, (msg) => {
    vscode.window.showInformationMessage(msg, "OK");
    return "processed " + msg;
  });
    let vtp = new VisualizerProvider(c, "demo");
    let view = vscode.window.createTreeView(
      'nodeDependencies', {
        treeDataProvider: vtp,
        canSelectMany: true,
        showCollapseAll: true,
      }
    );
    view.message = "Showing Visualizers!";
    view.onDidChangeSelection((ev) => {
      if (ev.selection.length > 0) {
          view.message = `Selected ${ev.selection[0].label}`;
      }
    });
    view.title = "Showing Visualizers!";

    vscode.commands.registerCommand("nodeDependencies.deleteEntry", async function (this: any, args: any) {
        let v = args as Visualizer;
        let ok = await c.sendRequest(NodeInfoRequest.destroy, v.data.id);
        if (ok) {
          this.refresh(v.parent);
        } else {
          vscode.window.showErrorMessage('Cannot delete node ' + v.label);
        }
    }, vtp);
}

