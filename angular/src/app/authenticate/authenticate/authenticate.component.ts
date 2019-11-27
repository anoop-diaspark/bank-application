import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HomeService } from 'src/app/home/home.service';
import { first } from 'rxjs/operators';
import { DataService } from 'src/app/routine/dataservice.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.css']
})


export class AuthenticateComponent implements OnInit {
  form: FormGroup;
  submitted = false;
  error = '';
  loading = false
  issubmitted = false
  showbutton = false;
  button ="LOG IN"

  constructor(public homeservice: HomeService,
    public fb: FormBuilder, public router: Router, public dataService: DataService) { }



  ngOnInit() {
    this.form = this.fb.group({

      'userName': ['', [Validators.required]],
      'password': ['', [Validators.required]]
    })

  };
  get f() {
    return this.form.controls;
  }
  registerClick() {
    this.router.navigate(['/signup'])
  }
  printError(status) {
    if (status === 400) {
      this.error = `Invalid Credentials`

    } else if (status === 404) {
      this.error = `Invalid Credentials`

    } else if (status === 501) {
      this.error = `Server Error`

    }
    else {
      this.error = `Data Server Error`
    }
  }
  onSubmit() {
    this.submitted = true
    console.log(this.form.value);
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.button="Logging "
    this.homeservice.authenticate(this.form.value).pipe(first()).subscribe(
      res => {
        this.issubmitted = true
        this.showbutton = true
        this.dataService.sendMessage(this.showbutton)
        alert(`Authentication Successful`)
        this.router.navigate(["/routine"]);
      }, (error: HttpErrorResponse) => {
        console.log(error.error.status);
        this.error = error.error.status
        this.printError(this.error)
        this.button="LOG IN"
        this.loading = false;
      }
    );
  }
}
