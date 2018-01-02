import {Component, OnInit} from '@angular/core';
import {RemoteService} from './remote.service';
import {ActivatedRoute} from '@angular/router';
import {Decision} from './domain.type';

@Component({
  templateUrl: './decision-view.component.html'
})
export class DecisionViewComponent implements OnInit {
  decision: Decision;

  constructor(private route: ActivatedRoute, private remoteService: RemoteService) {
    console.log('Decision component starting now');
  }

  json(arg: any) {
    return JSON.stringify(arg);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      console.log('Loading decision:', id);
      this.remoteService.getDecision(id).subscribe(d => this.decision = d);
    });
  }
}

