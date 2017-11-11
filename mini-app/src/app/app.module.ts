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
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {BinderQueryFormComponent} from "./form.component";
import {ButtonModule, InputTextModule, PanelModule, ToolbarModule} from "primeng/primeng";

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
        CourtPanelComponent,

        BinderQueryFormComponent,

    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterModule.forRoot(routes),
        HttpModule,
        FormsModule,

        PanelModule,
        ButtonModule,
        InputTextModule,
        ToolbarModule

    ],
    providers: [
        RemoteService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
