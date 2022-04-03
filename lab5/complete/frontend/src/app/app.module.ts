import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MoviesComponent} from "./movie/movies.component";
import {StarRatingModule} from 'angular-star-rating';
import {AboutComponent} from "./about/about.component";
import { MovieComponent } from './movie/movie.component';

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    MoviesComponent,
    MovieComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    StarRatingModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
