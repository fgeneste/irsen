/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { EtatCivil } from '@/shared/model/etat-civil.model';

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
  describe('EtatCivil Service', () => {
    let service: EtatCivilService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new EtatCivilService();
      currentDate = new Date();
      elemDefault = new EtatCivil(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateNaissance: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
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

      it('should create a EtatCivil', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateNaissance: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a EtatCivil', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a EtatCivil', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            civilite: 'BBBBBB',
            titre: 'BBBBBB',
            nomFamille: 'BBBBBB',
            nomMarital: 'BBBBBB',
            nomUsuel: 'BBBBBB',
            prenoms: 'BBBBBB',
            prenomUsuel: 'BBBBBB',
            dateNaissance: dayjs(currentDate).format(DATE_FORMAT),
            communeNaissance: 'BBBBBB',
            profession: 'BBBBBB',
            courriel: 'BBBBBB',
            courriel2: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a EtatCivil', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a EtatCivil', async () => {
        const patchObject = Object.assign(
          {
            titre: 'BBBBBB',
            nomFamille: 'BBBBBB',
            nomMarital: 'BBBBBB',
            communeNaissance: 'BBBBBB',
            courriel: 'BBBBBB',
            courriel2: 'BBBBBB',
          },
          new EtatCivil()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a EtatCivil', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of EtatCivil', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            civilite: 'BBBBBB',
            titre: 'BBBBBB',
            nomFamille: 'BBBBBB',
            nomMarital: 'BBBBBB',
            nomUsuel: 'BBBBBB',
            prenoms: 'BBBBBB',
            prenomUsuel: 'BBBBBB',
            dateNaissance: dayjs(currentDate).format(DATE_FORMAT),
            communeNaissance: 'BBBBBB',
            profession: 'BBBBBB',
            courriel: 'BBBBBB',
            courriel2: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateNaissance: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of EtatCivil', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a EtatCivil', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a EtatCivil', async () => {
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
