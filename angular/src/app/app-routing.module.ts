import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from '../app/home/home.component'
import { ListComponent } from './list/list.component';
import { RoutineComponent} from '../app/routine/routine.component'
import { AuthenticateComponent } from './authenticate/authenticate/authenticate.component';
import { UploaddownloadfileComponent } from './upload/uploaddownloadfile/uploaddownloadfile.component';
import { SignupComponent } from './signup/signup.component';
import {AuthGuard} from 'src/auth/auth.guard'


const routes: Routes = [
  
  {
    path:'',
    component:AuthenticateComponent

  },
  {
    path:'getlist',
    component:ListComponent,
    canActivate: [AuthGuard] 

  },
  {
    path:'routine',
    component:RoutineComponent,
    canActivate: [AuthGuard] 
  },
{
  path:'auth',
  component:AuthenticateComponent
},
{
  path:'upload',
  component:UploaddownloadfileComponent,
  // canActivate: [AuthGuard] 
},
{
  path:'signup',
  component:SignupComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
