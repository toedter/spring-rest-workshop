import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Movie} from './movie';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  constructor(private http: HttpClient) {
  }

  public getMovies(url?: string): Observable<Movie[]> {
    let uri = '/api/movies';
    if (url) {
      uri = url;
    }

    return this.http.get(uri).pipe(
      map((response: any) => response),
      catchError(this.handleError));
  }

  private handleError(error: any) {
    const errMsg = 'MovieService: cannot get users from http server.';
    console.error(errMsg); // log to console instead
    return throwError(errMsg);
  }
}
