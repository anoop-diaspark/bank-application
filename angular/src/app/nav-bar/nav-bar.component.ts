import { Component, OnInit } from '@angular/core';
import { Routes, RouterModule, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Subscription } from 'rxjs';
import { DataService } from '../routine/dataservice.service';
import{HomeService} from '../home/home.service'
@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  loggedIn = new BehaviorSubject<boolean>(false); // {1}
  showbutton = false;
  messages: any[] = [];
  subscription: Subscription;
  isSubmitted
  login:any


  get isLoggedIn() {
    return this.loggedIn.asObservable(); // {2}

  }
   




  constructor(private router: Router, private dataService: DataService,public homeservice:HomeService) {

    this.subscription = this.dataService.getMessage().subscribe(message => {
      if (message) {
        console.log("submitted value", message)
        this.showbutton = message.text
      } else {
        // clear messages when empty message receive
        this.messages = [];
      }
    })  
  }
  
  logout() {
    sessionStorage.removeItem('token');
    alert(`Logging you out !!`)
    this.router.navigate(['/auth']);
    this.showbutton = false

  }

  ngOnInit() {
    if (sessionStorage.getItem('token') != null) { // {3}
      this.loggedIn.next(true);
      this.router.navigate(['/routine']);
      console.log(`ldld`, this.loggedIn)
      this.showbutton = true


    }
  }

}
