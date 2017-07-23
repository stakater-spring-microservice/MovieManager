import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Genre } from './genre.model';
import { GenrePopupService } from './genre-popup.service';
import { GenreService } from './genre.service';
import { Movie, MovieService } from '../movie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-genre-dialog',
    templateUrl: './genre-dialog.component.html'
})
export class GenreDialogComponent implements OnInit {

    genre: Genre;
    isSaving: boolean;

    movies: Movie[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private genreService: GenreService,
        private movieService: MovieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.movieService.query()
            .subscribe((res: ResponseWrapper) => { this.movies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.genre.id !== undefined) {
            this.subscribeToSaveResponse(
                this.genreService.update(this.genre));
        } else {
            this.subscribeToSaveResponse(
                this.genreService.create(this.genre));
        }
    }

    private subscribeToSaveResponse(result: Observable<Genre>) {
        result.subscribe((res: Genre) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Genre) {
        this.eventManager.broadcast({ name: 'genreListModification', content: 'OK'});
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

    trackMovieById(index: number, item: Movie) {
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
    selector: 'jhi-genre-popup',
    template: ''
})
export class GenrePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genrePopupService: GenrePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.genrePopupService
                    .open(GenreDialogComponent as Component, params['id']);
            } else {
                this.genrePopupService
                    .open(GenreDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
