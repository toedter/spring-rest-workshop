import {Component, OnInit} from '@angular/core';
import {Movie} from './movie';
import {MovieService} from './movie.service';


@Component({
  selector: 'app-users',
  templateUrl: 'movies.component.html',
  providers: [MovieService],
})
export class MoviesComponent implements OnInit {
  movies: Movie[] = [];
  page: any = {};
  links: any = {};

  constructor(private usersService: MovieService) {
    console.log('MoviesComponent');
  }

  ngOnInit() {
    this.getMovies();
  }

  getMovies(url?: string) {
    this.usersService.getMovies(url)
      .subscribe(
        (response: any) => {
          this.movies = response.data;
          this.page = response.meta.page;
          this.links = response.links;
        },
        error => console.error('MoviesComponent: cannot get users from UserService'));
  }

  getMoviesByPage(page: number) {
    this.getMovies('/api/movies?page[number]=' + page);
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
