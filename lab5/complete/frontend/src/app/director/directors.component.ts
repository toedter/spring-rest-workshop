import {Component, OnInit} from '@angular/core';
import {Director} from './director';
import {DirectorService} from './director.service';

@Component({
  selector: 'app-directors',
  templateUrl: 'directors.component.html',
  providers: [DirectorService],
})
export class DirectorsComponent implements OnInit {
  directors: Director[] = [];
  page: any = {};
  included: any[] = [];
  links: any = {};

  constructor(private directorService: DirectorService) {
  }

  ngOnInit() {
    this.getDirectors();
  }

  getDirectors(url?: string) {
    this.directorService.getDirectors(url)
      .subscribe(
        (response: any) => {
          this.directors = response.data;
          this.page = response.meta.page;
          this.links = response.links;
          this.included = response.included;
        },
        error => console.error('MoviesComponent: cannot get users from UserService'));
  }

  getDirectorsByPage(page: number) {
    this.getDirectors('/api/directors?page[number]=' + page);
  }

  getMovies(director: any) {
   return this.directorService.getMovies(director, this.included);
  }

  getMinPage(): number {
    let minPage = 0;
    if (this.page.number > 5) {
      minPage = this.page.number - 5;
    }

    if (this.page.number > this.page.totalPages - 5) {
      minPage = this.page.totalPages - 10;
    }

    return minPage;
  }

  getPages(): number[] {
    return new Array<number>(this.getMaxPage() - this.getMinPage());
  }

  private getMaxPage(): number {
    return this.getMinPage() + 10;
  }
}
