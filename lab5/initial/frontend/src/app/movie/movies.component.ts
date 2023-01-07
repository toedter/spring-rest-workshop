import {Component, OnInit} from '@angular/core';
import {Movie} from './movie';
import {MovieService} from './movie.service';

@Component({
  selector: 'app-movies',
  templateUrl: 'movies.component.html',
  providers: [MovieService],
})
export class MoviesComponent implements OnInit {
  movies: Movie[] = [];
  page: any = {};
  included: any[] = [];
  links: any = {};

  constructor(private movieService: MovieService) {
  }

  ngOnInit() {
    this.getMovies();
  }

  getMovies(url?: string) {
    // subscribe to movie service and get movies
  }

  getMoviesByPage(page: number) {
    this.getMovies('/api/movies?page[number]=' + page);
  }

  getDirectors(movie: any) {
   return this.movieService.getDirectors(movie, this.included);
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
