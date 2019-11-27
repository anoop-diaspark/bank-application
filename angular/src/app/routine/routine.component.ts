import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { HomeService } from '../home/home.service';
import { Customer } from '../home/home.service';
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ScheduelService } from './scheduel.service';
import { ScehduelComponent } from './scehduel/scehduel.component';
import { ScheduleeditComponent } from './scheduleedit/scheduleedit.component';
import { DataService } from './dataservice.service';
import { DataSource } from '@angular/cdk/collections';
import { DeletedialogComponent } from './deletedialog/deletedialog.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-routine',
  templateUrl: './routine.component.html',
  styleUrls: ['./routine.component.css'],

})


export class RoutineComponent implements OnInit {
  checkboxmark;
  dropdownList = [];
  dropdownList2 = [];
  selectedItems1 = [];

  selectedItems2 = [];
  groupValue: string[] = []
  groupValue1: string[] = []
  dropdownSettings1 = {

  };
  pagenumber = 10;
  loading = false;



  dropdownSettings2 = {

  };
  list: any[];

  displayedColumns: string[] = [ 'countryName', 'matchDate', 'matchType',"matchStatus", 'actions'];
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  selection = new SelectionModel<Customer>(true, []);
  searchKey: string;
  dataSource = new MatTableDataSource<Customer>(this.list);
  constructor(
    private _HomeService: HomeService, private dialog: MatDialog, private scheduelService: ScheduelService, private dataService: DataService, private router: Router) { }

  ngOnInit() {

    this.dropdownList = [
      { "id": 1, "itemName": "India", countryName: "India" },
      { "id": 2, "itemName": "USA", countryName: "USA" },
      { "id": 3, "itemName": "Germany", countryName: "Germany" },
      { "id": 4, "itemName": "Mexico", countryName: "England" },
      { "id": 5, "itemName": "Sri Lanka", countryName: "Sri Lanka" },
    ];

    this.dropdownList2 = [
      { "id": 1, "itemName": "t20" },
      { "id": 2, "itemName": "odi" },
      { "id": 3, "itemName": "test" }
    ];
    this.dropdownSettings1 = {
      singleSelection: false,
      text: "Select Country",
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      enableSearchFilter: true,
      classes: "myclass custom-class"
    };
    this.dropdownSettings2 = {
      text: "Select Format",
      singleSelection: false,
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      enableSearchFilter: true,
      classes: "myclass custom-class",
      escapeToClose	:true
    };
  }
  logout() {
    this._HomeService.logout();
    this.router.navigate(["/auth"]);
  }
  onCreate() {
    this.scheduelService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(ScehduelComponent, dialogConfig).afterClosed().subscribe(() => {
      this.getdatabypage(this.pagenumber)
    })

  }
  onEdit(row) {
    console.log(row);
    this.scheduelService.populateForm(row);
    this.scheduelService.forid(row);
    this.dataService.sendMessage(row.id);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(ScheduleeditComponent, dialogConfig).afterClosed().subscribe(() => {
      this.getdatabypage(this.pagenumber)
    })
  }
  getPageData(event) {

    this.pagenumber = event.pageSize;
    this.loading = true;
    this._HomeService.getallmatchdetails1(this.pagenumber, this.products).subscribe(res => {
      this.list = res;
      this.loading = false;

      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );
  }
  getdatabypage(pagesize) {
    this.loading = true;
    this._HomeService.getallmatchdetails1(pagesize, this.products).subscribe(res => {
      this.list = res;
      this.loading = false;

      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );
  }



  delete(row) {
    this.scheduelService.populateForm(row);
    this.dataService.sendMessage1(this.pagenumber);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(DeletedialogComponent, dialogConfig).afterClosed().subscribe(() => {
      this.getdatabypage(this.pagenumber)
    })
  }
  products = {
    countryName: [],
    matchType: []
  }
  onItemSelect1(item: any) {
    let result1 = this.selectedItems1.map(({ itemName }) => itemName)
    let result2 = this.selectedItems2.map(({ itemName }) => itemName)

    this.products.countryName = result1
    this.products.matchType = result2
    this.loading = true;
    this._HomeService.getallmatchdetails1(this.pagenumber, this.products).subscribe(res => {
      this.loading = false;
      this.list = res;
    

      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );



  }


  OnItemDeSelect1(item: any) {

    let result1 = this.selectedItems1.map(({ itemName }) => itemName)
    let result2 = this.selectedItems2.map(({ itemName }) => itemName)
    this.products.countryName = result1
    this.products.matchType = result2
    this.loading = true;
    this._HomeService.getallmatchdetails1(this.pagenumber, this.products).subscribe(res => {
      this.loading=false;
      this.list = res;
      

      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );


  }
  OnItemDeSelect2(item: any) {
    let result1 = this.selectedItems1.map(({ itemName }) => itemName)
    let result2 = this.selectedItems2.map(({ itemName }) => itemName)
    this.products.countryName = result1
    this.products.matchType = result2
    this.loading = true;
    this._HomeService.getallmatchdetails1(this.pagenumber, this.products).subscribe(res => {
      this.loading=false;

      this.list = res;
     
      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );


  }
  onSelectAll1(items: any) {
    let result1 = this.selectedItems1.map(({ itemName }) => itemName);
    this.products.countryName = result1;
    this.loading = true;
    this._HomeService.getallmatchdetails1(this.pagenumber, this.products).subscribe(res => {
      this.list = res;
      this.loading=false;

      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );


  }
  onDeSelectAll2(items: any) {
    let result1 = this.selectedItems1.map(({ itemName }) => itemName)
    let result2 = this.selectedItems2.map(({ itemName }) => itemName)
    this.products.countryName = result1
    this.products.matchType = result2
    this._HomeService.getallmatchdetails1(this.pagenumber, this.products).subscribe(res => {
      this.list = res

      this.dataSource = new MatTableDataSource<Customer>(this.list);
    },

    );


  }

}
