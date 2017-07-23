import { BaseEntity } from './../../shared';

export class Movie implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public released?: number,
        public url?: string,
        public genres?: BaseEntity[],
    ) {
    }
}
