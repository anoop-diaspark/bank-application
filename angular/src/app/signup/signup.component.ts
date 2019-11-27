import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validator, Validators } from '@angular/forms'
import { first } from 'rxjs/operators'
import { Router } from '@angular/router';
import { HomeService } from '../home/home.service'
// import custom validator to validate that password and confirm password fields match
import { MustMatch } from 'src/app/_helpers/must-match.validator';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  userRegistraton: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  button="Register"
  error: any;
  message: any;

  constructor(private formBuilder: FormBuilder,
    private router: Router, private _HomeService: HomeService) { }

  ngOnInit() {
    this.userRegistraton = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      userName: ['', Validators.required],

      emailId: ['', [Validators.required, Validators.email]],
      mobileNumber: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(10)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.pattern(/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: MustMatch('password', 'confirmPassword')
    });
  }
  // convenience getter for easy access to form fields
  get f() { return this.userRegistraton.controls; }
  onSubmit() {

    this.submitted = true;

    // stop here if form is invalid
    if (this.userRegistraton.invalid) {
      return;
    }

    this.loading = true;
    this.button="Please wait ..."
    this._HomeService.userRegistration(this.userRegistraton.value)
      .pipe(first())
      .subscribe(
        res => {
          console.log(res)
          this.loading = false;
          alert(`user registered successfully !`);
          this.button="Register"
          this.router.navigate([""]);
        },
        error => {
          console.log(error)
          this.error = error;
          this.loading = false;
          this.button="Register"
        });
  }

}
