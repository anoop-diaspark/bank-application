import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { HomeComponent } from '../home/home.component';


import { map } from 'rxjs/operators';

import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class Customer {
  countyName: string
  matchType: string
  matchDate: string
}
@Injectable({
  providedIn: 'root'
})
export class HomeService {
  _url = 'http://192.168.96.222:8080/country'
  _urls = 'http://192.168.96.222:8080/fetch/alldata';
  //get list of match detail ---
  _url3 = "http://192.168.96.222:8080/fetch/allmatchdata?pageNumber=4";
  registrationUrl = " http://192.168.96.222:8080/register"
  constructor(private http: HttpClient) { }
  private handleError(errorResponse: HttpErrorResponse) {

    if (errorResponse.error instanceof ErrorEvent) {
      alert(`Client Side Error ${errorResponse.error.message}`);
    } else if (errorResponse.status == 404) {
      alert(`Not Found`)
    }
    else if (errorResponse.status == 401) {
      alert(`Please Authorize !`)
    }

    else {
      alert(`Please Login First !`)
    }
    return Observable.throw(`Server is not working Properly`)

  }

  registeration(reg: HomeComponent) {
    return this.http.post<any>(this._url, reg);
  }
  userRegistration(userRegistrationData) {
    return this.http.post(this.registrationUrl, userRegistrationData);
  }
  countrylist1(acno: String) {
    // return this.http.get("http://192.168.96.222:8080/fetch?countryName="+`${acno}`+"");
    return this.http.get(`http://192.168.96.222:8080/fetch?countryName=${acno}`)
  }
  countrylist2(acno: String, acno1: String) {
    return this.http.get(`http://192.168.96.222:8080/fetch/two?countryName=${acno}&countryName1=${acno1}`)
  }

  countrylist3(acno: String, acno1: String, acno2: String) {
    return this.http.get(`http://192.168.96.222:8080/fetch/three?countryName=${acno}&countryName1=${acno1}&countryName2=${acno2}`)
  }

  countrylist4(acno: String, acno1: String, acno2: String, acno3: String) {
    return this.http.get(`http://192.168.96.222:8080/fetch/four?countryName=${acno}&countryName1=${acno1}&countryName2=${acno2}&countryName3=${acno3}`)
  }
  getall() {
    // return this.http.get("http://192.168.96.222:8080/fetch?countryName="+`${acno}`+"");
    return this.http.get<any>(this._urls)
  }

  getallmatchdetails(acno: number) {
    // return this.http.get("http://192.168.96.222:8080/fetch?countryName="+`${acno}`+"");

    return this.http.get<any>(`http://192.168.96.222:8080/fetch/allmatchdata?pageNumber=${acno}`)
  }
  getallmatchdetails1(acno: number, products: any) {
    // return this.http.get("http://192.168.96.222:8080/fetch?countryName="+`${acno}`+"");

    return this.http.post<any>(`http://192.168.96.222:8080/match?pageNumber=${acno}`, products)
  }

  authenticate(schedule) {
    return this.http.post<any>('http://192.168.96.222:8080/authenticate', schedule).pipe(
      map(
        userData => {
          sessionStorage.setItem('username', schedule);
          let tokenStr = 'Bearer ' + userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        }
      )

    );
  }
  islogin() {
    return !!sessionStorage.getItem('token')
  }

  logout() {
    if (sessionStorage.getItem('token')) {
      sessionStorage.removeItem("token")
    }

  }
  postFile(fileToUpload: File) {

    const endpoint = `http://192.168.96.222:8080/uploadFile`;
    const formData: FormData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);
    return this.http
      .post<any>(endpoint, formData)

  }
  downloadFile(file) {
    var body = file;
    return this.http.get(`http://192.168.96.222:8080/downloadFile/${body}`, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    })
  }
  downloadExcelFile() {
    return this.http.get(`http://192.168.96.222:8080/schedule/download/matchschedule.xlsx`, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Content-Type', `	
    application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`)
    })

  }
}

