/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import AdresseFiscaleService from '@/entities/adresse-fiscale/adresse-fiscale.service';
import { AdresseFiscale } from '@/shared/model/adresse-fiscale.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('AdresseFiscale Service', () => {
    let service: AdresseFiscaleService;
    let elemDefault;

    beforeEach(() => {
      service = new AdresseFiscaleService();
      elemDefault = new AdresseFiscale(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a AdresseFiscale', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a AdresseFiscale', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a AdresseFiscale', async () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            bister: 'BBBBBB',
            complement1: 'BBBBBB',
            complement2: 'BBBBBB',
            typeVoie: 'BBBBBB',
            voie: 'BBBBBB',
            codePostal: 'BBBBBB',
            ville: 'BBBBBB',
            pays: 'BBBBBB',
            affichageInternet: true,
            affichageIntranet: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a AdresseFiscale', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a AdresseFiscale', async () => {
        const patchObject = Object.assign(
          {
            numero: 'BBBBBB',
            complement2: 'BBBBBB',
            typeVoie: 'BBBBBB',
            voie: 'BBBBBB',
            ville: 'BBBBBB',
            affichageIntranet: true,
          },
          new AdresseFiscale()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a AdresseFiscale', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of AdresseFiscale', async () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            bister: 'BBBBBB',
            complement1: 'BBBBBB',
            complement2: 'BBBBBB',
            typeVoie: 'BBBBBB',
            voie: 'BBBBBB',
            codePostal: 'BBBBBB',
            ville: 'BBBBBB',
            pays: 'BBBBBB',
            affichageInternet: true,
            affichageIntranet: true,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of AdresseFiscale', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a AdresseFiscale', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a AdresseFiscale', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
