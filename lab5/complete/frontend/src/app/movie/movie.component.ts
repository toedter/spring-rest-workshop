import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MovieService} from "./movie.service";
import {Movie} from "./movie";
import {UntypedFormBuilder, UntypedFormGroup} from '@angular/forms';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  movie: Movie | undefined;
  form: UntypedFormGroup;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private movieService: MovieService,
    private router: Router,
    private route: ActivatedRoute) {
    this.form = this.formBuilder.group({
      title: [''],
      year: [''],
      imdbId: [''],
      rating: [''],
      rank: [''],
      thumb: ['']
    });
  }

  ngOnInit(): void {
    this.getMovie();
  }

  getMovie() {
    const id: string | null = this.route.snapshot.paramMap.get('id');
    this.movieService.getMovieById(id)
      .subscribe({
        next: (response: any) => {
          this.movie = response;
          this.form = this.formBuilder.group({
            title: [this.movie?.attributes.title],
            year: [this.movie?.attributes.year],
            imdbId: [this.movie?.attributes.imdbId],
            rating: [this.movie?.attributes.rating],
            rank: [this.movie?.attributes.rank],
            thumb: [this.movie?.attributes.thumb]
          });

        },
        error: error => console.error('MoviesComponent: cannot get users from UserService')
      });
  }

  deleteMovie(movie: Movie) {
    const href = (movie as any).links?.self?.href;
    if (href) {
      this.movieService.deleteMovie(href);
      this.router.navigate(['/movies']);
    }
  }

  updateMovie(movie: Movie) {
    const href = (movie as any).links?.self?.href;
    if (href) {
      movie.attributes = this.form.value;
      this.movieService.updateMovie(movie);
      // this.router.navigate(['/movies']);
    }
  }
}
