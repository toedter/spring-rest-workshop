import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class DirectorService {
  constructor(private http: HttpClient) {
  }

  public getDirectors(url?: string): Observable<any[]> {
    let uri = '/api/directors';
    if (url) {
      uri = url;
    }

    return this.http.get(uri).pipe(
      map((response: any) => response),
      catchError(this.handleError));
  }

  public getMovies(director: any, included: any[]): any[] {
    let result = [];
    const movies: any = director.relationships.movies.data;
    for (let movie of movies) {
      for (let includedMovie of included) {
        if (movie.type === includedMovie.type && movie.id === includedMovie.id) {
          result.push(includedMovie);
        }
      }
    }
    return result;
  }

  private handleError(error: any) {
    const errMsg = 'DirectorService: cannot get directors from http server.';
    console.error(errMsg); // log to console instead
    return throwError(errMsg);
  }
}
