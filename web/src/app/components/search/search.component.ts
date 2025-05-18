import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { Transaction } from '../../models/Transaction';
import { SearchService } from '../../services/search.service';
import { FormsModule } from '@angular/forms';
import { CommonModule} from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    FormsModule,
    CommonModule
  ],
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss'
})
export class SearchComponent implements AfterViewInit  {

  searchId = '';
  transactions: Transaction[] = [];
  dataSource = new MatTableDataSource<Transaction>();
  loading = false;

  displayedColumns = ['primaryId', 'secondaryId', 'eventType', 'timestamp', 'stepRank', 'eventRank', 'dateError'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private searchService: SearchService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }


  search() {
    if (!this.searchId) return;
    this.loading = true;
    this.searchService.getTransactionsById(this.searchId).subscribe({
      next: data => {
        this.transactions = data;
        this.dataSource.data = data;
        this.loading = false;
      },
      error: () => alert("Erreur lors de la recherche.")
    }); 
  };

  loadInvalid() {
    this.loading = true;
    this.searchService.getInvalidTransactions().subscribe({
      next: data => {
        this.transactions = data;
        this.dataSource.data = data;
        this.loading = false;
      },
      error: () => alert("Erreur lors du chargement des erreurs.")
    });
  }

}
