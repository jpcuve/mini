import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule, Routes} from '@angular/router';
import {QueryViewComponent} from './query-view.component';
import {RemoteService} from './remote.service';
import {
  ActorPanelComponent,
  BinderPanelComponent,
  CopyrightPanelComponent,
  CourtPanelComponent,
  DayPanelComponent,
  DecisionPanelComponent,
  DesignModelPanelComponent,
  DocketPanelComponent,
  DomainNamePanelComponent,
  ImagesPanelComponent,
  PartyPanelComponent,
  PatentPanelComponent,
  RightPanelComponent,
  TrademarkPanelComponent
} from './panel.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';
import {BinderQueryFormComponent} from './form.component';
import {ButtonModule, InputTextModule, PanelModule, ToolbarModule, TreeModule} from 'primeng/primeng';
import {HttpClientModule} from '@angular/common/http';
import {DecisionViewComponent} from './decision-view.component';

const routes: Routes = [
  {path: '', redirectTo: 'query', pathMatch: 'full'},
  {path: 'query', component: QueryViewComponent},
  {path: 'decision/:id', component: DecisionViewComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    QueryViewComponent,
    DecisionViewComponent,

    BinderPanelComponent,
    DayPanelComponent,
    DocketPanelComponent,
    DecisionPanelComponent,
    CourtPanelComponent,
    PartyPanelComponent,
    ActorPanelComponent,
    RightPanelComponent,
    ImagesPanelComponent,
    TrademarkPanelComponent,
    DomainNamePanelComponent,
    PatentPanelComponent,
    DesignModelPanelComponent,
    CopyrightPanelComponent,

    BinderQueryFormComponent,

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule,

    PanelModule,
    ButtonModule,
    InputTextModule,
    ToolbarModule,
    TreeModule,

  ],
  providers: [
    RemoteService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
