import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {QueryViewComponent} from "./query-view.component";
import {RemoteService} from "./remote.service";
import {HttpModule} from "@angular/http";
import {
    BinderPanelComponent, CourtPanelComponent, DecisionPanelComponent,
    DocketPanelComponent
} from "./panel.component";

const routes: Routes = [
    {path: '**', component: QueryViewComponent}
];

@NgModule({
    declarations: [
        AppComponent,
        QueryViewComponent,
        BinderPanelComponent,
        DocketPanelComponent,
        DecisionPanelComponent,
        CourtPanelComponent
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
