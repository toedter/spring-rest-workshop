import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MovieService} from "./movie.service";
import {Movie} from "./movie";

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  movie: Movie | undefined;

  constructor(private route: ActivatedRoute, private movieService: MovieService) {
  }

  ngOnInit(): void {
    this.getMovie();
  }

  getMovie() {
    const id: string | null = this.route.snapshot.paramMap.get('id');
    this.movieService.getMovieById(id)
      .subscribe(
        (response: any) => {
          this.movie = response;
        },
        error => console.error('MoviesComponent: cannot get users from UserService'));
  }
}
