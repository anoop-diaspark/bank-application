import { Component, OnInit } from '@angular/core';
import { ScheduelService } from '../scheduel.service';
import { MatDialogRef } from "@angular/material";
import { DataService } from '../dataservice.service';
import { Subscription } from 'rxjs';
import { first } from 'rxjs/operators';
@Component({
  selector: 'app-scheduleedit',
  templateUrl: './scheduleedit.component.html',
  styleUrls: ['./scheduleedit.component.css'],
  // providers:[ScheduelService]
})
export class ScheduleeditComponent implements OnInit {
  data: String;
  id: number
  messages: any[] = [];
  loading=false
  subscription: Subscription;
  countryName: any=[
    {name: 'India', value:"India"},
    {name: 'USA',value:"USA" },
    {name: 'Germany', value:"Germany"},
    {name: 'mexico',value:"mexico"},
    { name: 'Sri Lanka', value: "Sri Lanka" },
  ];
  constructor(public service: ScheduelService, public dialogRef: MatDialogRef<ScheduleeditComponent>, private dataService: DataService) {
    this.subscription = this.dataService.getMessage().subscribe(message => {
      if (message) {
     
        this.id = message.text
      } else {
        // clear messages when empty message receive
        this.messages = [];
      }
    })
  }

  ngOnInit() {
  }
  onClear() {

    this.service.form.reset();
  
  }

  onSubmit() {
   
    this.loading=true;
    this.service.editdetail(this.id, this.service.form.value.countryName, this.service.form.value.matchDate, this.service.form.value.matchType,this.service.form.value.matchStatus)
    .pipe(first())
    .subscribe(res => {
      this.data = res;

      alert(`data updated succesfully`)
      error=>{
        console.log(`Something went wrong`);
        this.loading = false;

      }

    },

    );
    this.onClose();

  }

  onClose() {
    this.service.form.reset();
    this.service.initializeFormGroup();
    this.dialogRef.close();

  }
}
