import * as vscode from 'vscode';
import * as fs from 'fs';
import * as path from 'path';
import { LanguageClient } from 'vscode-languageclient';
import { NodeQueryRequest } from './protocol';

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
  constructor(
    public readonly label: string,
    private version: number,
    public ch: Visualizer[] | null,
    public readonly collapsibleState: vscode.TreeItemCollapsibleState
  ) {
    super(label, collapsibleState);
    this.tooltip = `${this.label}-${this.version}`;
    this.description = `Describe ${this.version}`;
  }

  contextValue = "node";

  iconPath = {
    light: path.join(__filename, '..', '..', 'resources', 'light', 'dependency.svg'),
    dark: path.join(__filename, '..', '..', 'resources', 'dark', 'dependency.svg')
  };
}

export function register(c : LanguageClient) {
  c.onRequest(NodeQueryRequest.type, (msg) => {
    vscode.window.showInformationMessage(msg, "OK");
    return "processed " + msg;
  });


    let v = new Visualizer('root', 33, [
      new Visualizer('chA', 1, null, vscode.TreeItemCollapsibleState.None),
      new Visualizer('chB', 2, [
        new Visualizer('deep1', 11, null, vscode.TreeItemCollapsibleState.None),
        new Visualizer('deep2', 22, null, vscode.TreeItemCollapsibleState.None),
      ], vscode.TreeItemCollapsibleState.Collapsed),
      new Visualizer('chC', 3, null, vscode.TreeItemCollapsibleState.None),
    ], vscode.TreeItemCollapsibleState.Expanded);
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
        v.description = 'Deleted!';
        this.refresh(v);
    }, vtp);
}

