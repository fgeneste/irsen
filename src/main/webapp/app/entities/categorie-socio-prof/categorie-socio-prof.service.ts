import axios from 'axios';

import { ICategorieSocioProf } from '@/shared/model/categorie-socio-prof.model';

const baseApiUrl = 'api/categorie-socio-profs';

export default class CategorieSocioProfService {
  public find(id: number): Promise<ICategorieSocioProf> {
    return new Promise<ICategorieSocioProf>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ICategorieSocioProf): Promise<ICategorieSocioProf> {
    return new Promise<ICategorieSocioProf>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ICategorieSocioProf): Promise<ICategorieSocioProf> {
    return new Promise<ICategorieSocioProf>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: ICategorieSocioProf): Promise<ICategorieSocioProf> {
    return new Promise<ICategorieSocioProf>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
