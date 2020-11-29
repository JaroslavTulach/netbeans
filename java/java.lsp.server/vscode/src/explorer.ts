import * as vscode from 'vscode';
import * as fs from 'fs';
import * as path from 'path';
import { LanguageClient } from 'vscode-languageclient';
import { NodeInfoRequest, NodeQueryRequest } from './protocol';

class VisualizerProvider implements vscode.TreeDataProvider<Visualizer> {
  constructor(private root: Visualizer) {}

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
      return Promise.resolve(element.ch || []);
    } else {
      return Promise.resolve([ this.root ]);
    }
  }
}

class Visualizer extends vscode.TreeItem {
  public ch : Visualizer[] | null;
  constructor(
    private data : NodeInfoRequest.Data
  ) {
    super(data.displayName, data.leaf ? vscode.TreeItemCollapsibleState.None : vscode.TreeItemCollapsibleState.Collapsed);
    this.id = data.name;
    this.description = data.shortDescription;
    this.ch = (data as any).ch; 
  }

  contextValue = "node";
}

export function register(c : LanguageClient) {
  c.onRequest(NodeQueryRequest.type, (msg) => {
    vscode.window.showInformationMessage(msg, "OK");
    return "processed " + msg;
  });
  class NodeData implements NodeInfoRequest.Data {
    displayName : string;
    shortDescription : string;
    leaf : boolean;

    constructor(
      public name : string,
      dispName : any,
      public ch : Visualizer[] | null
    ) {
      this.displayName = dispName.toString(); 
      this.shortDescription = 'Description for ' + this.displayName;
      this.leaf = ch === null;
    }
  }

    let v = new Visualizer(new NodeData('root', 33, [
      new Visualizer(new NodeData('chA', 1, null)),
      new Visualizer(new NodeData('chB', 2, [
        new Visualizer(new NodeData('deep1', 11, null)),
        new Visualizer(new NodeData('deep2', 22, null)),
      ])),
      new Visualizer(new NodeData('chC', 3, null)),
    ]));
    let vtp = new VisualizerProvider(v);
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

    vscode.commands.registerCommand("nodeDependencies.deleteEntry", function (this: any, args: any) {
        let v = args as Visualizer;
        v.description = 'Deleted! ';
        this.refresh(v);
      /*
        c.sendRequest(NodeInfoRequest.type, v.label).then((r) => {
          v.description = 'Deleted! ' + r;
          this.refresh(v);
        }, (err) => {
          vscode.window.showErrorMessage('Cannot delete node ' + err);
        });
        */
    }, vtp);
}

