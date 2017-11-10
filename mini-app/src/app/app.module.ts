import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {BindersViewComponent} from "./binders-view.component";
import {RemoteService} from "./remote.service";
import {HttpModule} from "@angular/http";
import {BinderPanelComponent, DecisionPanelComponent, DocketPanelComponent} from "./panel.component";

const routes: Routes = [
    {path: '**', component: BindersViewComponent}
];

@NgModule({
    declarations: [
        AppComponent,
        BindersViewComponent,
        BinderPanelComponent,
        DocketPanelComponent,
        DecisionPanelComponent
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(routes),
        HttpModule,


    ],
    providers: [
        RemoteService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
