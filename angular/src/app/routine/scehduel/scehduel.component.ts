import { Component, OnInit } from '@angular/core';
import { ScheduelService } from '../scheduel.service';
import { MatDialogRef } from "@angular/material";

@Component({
  selector: 'app-scehduel',
  templateUrl: './scehduel.component.html',
  styleUrls: ['./scehduel.component.css'],
  // providers:[ScheduelService]
})
export class ScehduelComponent implements OnInit {
  data: any;
  loading=false;
  countryName: any = [
    { name: 'India', value: "India" },
    { name: 'USA', value: "USA" },
    { name: 'Germany', value: "Germany" },
    { name: 'mexico', value: "mexico" },
    { name: 'Sri Lanka', value: "Sri Lanka" },
  ];


  constructor(public service: ScheduelService, public dialogRef: MatDialogRef<ScehduelComponent>) { }

  ngOnInit() {
  }
  onClear() {

    this.service.form.reset();
    this.service.initializeFormGroup();
  }

  onSubmit() {
    this.loading=true
    this.service.adddetail(this.service.form.value).subscribe(res => {
      this.data = res;
      alert(`data added successfully !`)
      error => {
        this.loading=false
        alert(`Data Not Added ! Check Connection`)
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
