// import { async, ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
// import { AuthenticateComponent } from './authenticate.component';
// import { FormsModule, FormGroup, ReactiveFormsModule, FormBuilder } from '@angular/forms';
// import { HomeService } from 'src/app/home/home.service';
// import { RouterTestingModule } from '@angular/router/testing';
// import { DataService } from 'src/app/routine/dataservice.service';
// import { HttpClientTestingModule } from '@angular/common/http/testing';

// import { Observable, of, from } from 'rxjs';

// describe("Authenticate Component", () => {
//   let component: AuthenticateComponent
//   let fixture: ComponentFixture<AuthenticateComponent>;

//   let service: HomeService;
//   let dataService: DataService;
//   let router: RouterTestingModule;
//   let form: FormGroup;

//   // beforeEach(async( () => {
//   //   TestBed.configureTestingModule({
//   //     declarations: [AuthenticateComponent],
//   //     imports: [HttpClientTestingModule, ReactiveFormsModule, RouterTestingModule],
//   //     providers: [DataService,HomeService]
//   //   })
//   //   fixture = TestBed.createComponent(AuthenticateComponent);
//   //   component = fixture.componentInstance;
//   //   dataService=TestBed.get(DataService);
//   //   service = TestBed.get(HomeService);
//   // }))



//   describe('AuthenticateComponent', () => {
//     let component: AuthenticateComponent;
//     let fixture: ComponentFixture<AuthenticateComponent>;
//     let service: HomeService;
//     let serviceNotification: DataService;
//     beforeEach(async(() => {
//       TestBed.configureTestingModule({
//         declarations: [AuthenticateComponent],
//         imports: [HttpClientTestingModule, FormsModule, HttpClientTestingModule, ReactiveFormsModule, RouterTestingModule],
//         providers: [DataService, HomeService, FormBuilder],

//       }).compileComponents().then(() => {
//         fixture = TestBed.createComponent(AuthenticateComponent);
//         component = fixture.componentInstance;
//       });
//       fixture = TestBed.createComponent(AuthenticateComponent);
//       component = fixture.componentInstance;
//       component.ngOnInit();
//       service = TestBed.get(HomeService);
//       serviceNotification = TestBed.get(DataService);
//     }));
//     it('should create', () => {
//       expect(component).toBeTruthy();
//     });

   

//     it('should set products property with the items returned from the server', fakeAsync(() => {
     
//       component.form.controls['userName'].setValue('kanhaiya')
//       component.form.controls['password'].setValue('12345678')
 
//     })


//     )
//     // it('should update model on submit', fakeAsync(() => {
//     //   updateForm(validUser.userName, validUser.password);
//     //   component.onSubmit();
//     //   expect(component.form.value).toEqual(validUser);
//     // }));



//   })
// })
