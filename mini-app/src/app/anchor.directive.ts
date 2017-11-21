import {Directive, ViewContainerRef} from "@angular/core";

@Directive({
    selector: '[anchor]'
})
export class AnchorDirective {

    constructor(public viewContainer: ViewContainerRef){
    }

}
