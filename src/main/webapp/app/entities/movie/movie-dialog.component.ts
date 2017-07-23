import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Movie } from './movie.model';
import { MoviePopupService } from './movie-popup.service';
import { MovieService } from './movie.service';
import { Genre, GenreService } from '../genre';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-movie-dialog',
    templateUrl: './movie-dialog.component.html'
})
export class MovieDialogComponent implements OnInit {

    movie: Movie;
    isSaving: boolean;

    genres: Genre[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private movieService: MovieService,
        private genreService: GenreService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.genreService.query()
            .subscribe((res: ResponseWrapper) => { this.genres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.movie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.movieService.update(this.movie));
        } else {
            this.subscribeToSaveResponse(
                this.movieService.create(this.movie));
        }
    }

    private subscribeToSaveResponse(result: Observable<Movie>) {
        result.subscribe((res: Movie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Movie) {
        this.eventManager.broadcast({ name: 'movieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackGenreById(index: number, item: Genre) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-movie-popup',
    template: ''
})
export class MoviePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moviePopupService: MoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.moviePopupService
                    .open(MovieDialogComponent as Component, params['id']);
            } else {
                this.moviePopupService
                    .open(MovieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
