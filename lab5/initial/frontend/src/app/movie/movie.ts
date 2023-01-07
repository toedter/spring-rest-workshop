export class Movie {
  constructor(public id: string,
              public attributes: {
                imdbId: string,
                rank: string,
                rating: number,
                title: string,
                year: string,
                thumb: string
              },
              public links?: any) {
  }
}

