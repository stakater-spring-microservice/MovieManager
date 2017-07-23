import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MoviemanagerMovieModule } from './movie/movie.module';
import { MoviemanagerGenreModule } from './genre/genre.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MoviemanagerMovieModule,
        MoviemanagerGenreModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoviemanagerEntityModule {}
