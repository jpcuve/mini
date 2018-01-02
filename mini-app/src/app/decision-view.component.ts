import {Component, OnInit} from "@angular/core";
import {RemoteService} from "./remote.service";

@Component({
  templateUrl: './decision-view.component.html'
})
export class DecisionViewComponent implements OnInit {

  constructor(private remoteService: RemoteService) {
    console.log('Decision component starting now');
  }

  json(arg: any) {
    return JSON.stringify(arg);
  }

  ngOnInit(): void {
  }
}

