import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { HomeService } from 'src/app/home/home.service';
import { saveAs } from 'file-saver';
import { AuthGuard } from 'src/auth/auth.guard'
@Component({
  selector: 'app-uploaddownloadfile',
  templateUrl: './uploaddownloadfile.component.html',
  styleUrls: ['./uploaddownloadfile.component.css']
})
export class UploaddownloadfileComponent implements OnInit {

  @ViewChild('labelImport', { static: true })
  labelImport: ElementRef;
  button = 'Download Timesheet'
  attachmentList: any = [];
  fileToUpload: File = null;
  fileUrl;
  isSubmitted = false
  loading = false
  downloadfile = false;


  constructor(public homeService: HomeService, private authService: AuthGuard) { }

  ngOnInit() {
  }
  handleFileInput(files: FileList) {
    this.labelImport.nativeElement.innerText = Array.from(files)
      .map(f => f.name)
      .join(', ');
    this.fileToUpload = files.item(0);

  }

  uploadFileToActivity() {
    if (this.fileToUpload == null) {
      alert(`Please upload a file first`)
    } else {
      this.homeService.postFile(this.fileToUpload).subscribe(data => {
        this.attachmentList.push(data);
        this.isSubmitted = true;
        alert(`data submitted !`)
        this.fileToUpload = null;
        this.labelImport.nativeElement.innerText = 'Choose a File'
      }, error => {
        alert(`Data not submitted`)
      });
    }
  }
  data: any[]
  //download uploaded file
  download() {
    var filename = this.attachmentList[0].fileName;

    this.homeService.downloadFile(filename)
      .subscribe(
        data => saveAs(data, filename),
        error => console.log(`File not downloaded, something wrong with server`, error)
      )

  }
  //downloading timesheet excel
  downloadExcel() {


    this.loading = true
    this.button = 'Downloading';
    this.homeService.downloadExcelFile().subscribe(res => {
      saveAs(res, 'Timesheet.xlsx',
        { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
      this.loading = false;
      this.button = 'Download Timesheet';


    }, error => {
      setTimeout(() => {
        alert(`Server is not responding`)
        this.loading = false
        this.button = 'Download Timesheet';
      }, 5000);



    }

    )
  }
}
