import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploaddownloadfileComponent } from './uploaddownloadfile.component';
import { HomeService } from '../../home/home.service';
import { DataService } from 'src/app/routine/dataservice.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('Login Component', () => {
    let component: UploaddownloadfileComponent;
    let fixture: ComponentFixture<UploaddownloadfileComponent>;
    let homeservice: HomeService;


    beforeEach(async(() => {

        TestBed.configureTestingModule({
            imports: [
                HttpClientTestingModule,
                RouterTestingModule
            ],
            declarations: [
                UploaddownloadfileComponent
            ],
            providers: [
                HomeService,
                DataService
            ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(UploaddownloadfileComponent);
            homeservice = TestBed.get(HomeService);
            component = fixture.componentInstance;
            fixture.detectChanges();
        })
    }));



    it('should create', () => {
        expect(component).toBeTruthy();
    });
    it('should handle upload file activity', async(() => {
        var app = component.homeService;
        spyOn(app, 'downloadFile');
        expect(app.downloadFile).toHaveBeenCalled()
    }))





});