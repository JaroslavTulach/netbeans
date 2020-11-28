import * as vscode from 'vscode';
import * as fs from 'fs';
import * as path from 'path';

class VisualizerProvider implements vscode.TreeDataProvider<Visualizer> {
  constructor(private root: Visualizer) {}

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

  iconPath = {
    light: path.join(__filename, '..', '..', 'resources', 'light', 'dependency.svg'),
    dark: path.join(__filename, '..', '..', 'resources', 'dark', 'dependency.svg')
  };
}

export function register() {
    let v = new Visualizer('root', 33, [
      new Visualizer('chA', 1, null, vscode.TreeItemCollapsibleState.None),
      new Visualizer('chB', 2, [
        new Visualizer('deep1', 11, null, vscode.TreeItemCollapsibleState.None),
        new Visualizer('deep2', 22, null, vscode.TreeItemCollapsibleState.None),
      ], vscode.TreeItemCollapsibleState.Collapsed),
      new Visualizer('chC', 3, null, vscode.TreeItemCollapsibleState.None),
    ], vscode.TreeItemCollapsibleState.Expanded);
    let view = vscode.window.createTreeView(
      'nodeDependencies', {
        treeDataProvider: new VisualizerProvider(v),
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
}

