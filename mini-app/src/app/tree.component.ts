import {
    AfterViewInit, Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, Input, OnInit, Type,
    ViewChild
} from "@angular/core";
import {AnchorDirective} from "./anchor.directive";
import {TreeNodeComponent} from "./tree-node.component";
import {TreeNode} from "primeng/primeng";

@Component({
    selector: 'tree',
    template: `
<ul>
    <ng-template anchor></ng-template>
    <li *ngFor="let c of node.children">
        <tree [node]="c" [node-component]="nodeComponent"></tree>
    </li>
</ul>
`
})
export class TreeComponent implements OnInit, AfterViewInit {
    @Input()
    node: TreeNode;
    @ViewChild(AnchorDirective)
    anchor: AnchorDirective;
    @Input("node-component")
    nodeComponent: Type<TreeNodeComponent>;

    constructor(private componentFactoryResolver: ComponentFactoryResolver){
        console.log('Tree component starting now');
    }

    ngOnInit(): void {
        this.loadComponent();
    }


    ngAfterViewInit(): void {
        // had the loadComponent call before I moved it to ngOnInit
    }

    loadComponent(): void {
        let componentFactory: ComponentFactory<TreeNodeComponent> = this.componentFactoryResolver.resolveComponentFactory(this.nodeComponent);
        let viewContainer = this.anchor.viewContainer;
        viewContainer.clear();
        let componentRef: ComponentRef<TreeNodeComponent> = viewContainer.createComponent(componentFactory);
        let component: TreeNodeComponent = componentRef.instance;
        component.node = this.node;

    }

}
