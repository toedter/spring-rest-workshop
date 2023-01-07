import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Movie} from './movie';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  constructor(private http: HttpClient) {
  }

  public getMovies(url?: string): Observable<any[]> {
    let uri = '/api/movies';
    if (url) {
      uri = url;
    }

    // use http to get movies and catch errors
    return of([]);
  }

  public getMovieById(id: string | null): Observable<Movie> {
    let uri = '/api/movies/' + id;

    return this.http.get(uri).pipe(
      map((response: any) => new Movie(response.data.id, response.data.attributes, response.links)),
      catchError(this.handleError)
    );
  }

  public getDirectors(movie: any, included: any[]): any[] {
    let result = [];
    const directors: any = movie.relationships.directors.data;
    for (let director of directors) {

      for (let includedDirector of included) {
        if (director.type === includedDirector.type && director.id === includedDirector.id) {
          result.push(includedDirector.attributes.name);
        }
      }
    }
    return result;
  }

  public deleteMovie(href: string) {
    if (!window.location.host.startsWith('localhost')) {
      alert('Sorry, deleting movies in the cloud is not allowed.');
      return;
    }

    if (href) {
      this.http.delete(href).subscribe();
    }
  }

  updateMovie(movie: Movie) {
    if (!window.location.host.startsWith('localhost')) {
      alert('Sorry, updating movies in the cloud is not allowed.');
      return;
    }

    const href = (movie as any).links?.self;
    if (href) {
      this.http.patch(href, { data: movie }, {headers : new HttpHeaders({ 'Content-Type': 'application/vnd.api+json' })}).subscribe();
    }
  }

  private handleError(error: any) {
    const errMsg = 'MovieService: cannot get movies from http server.';
    console.error(errMsg); // log to console instead
    return throwError(errMsg);
  }
}
