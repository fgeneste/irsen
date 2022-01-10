/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';
import { MandatAncien } from '@/shared/model/mandat-ancien.model';

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
  describe('MandatAncien Service', () => {
    let service: MandatAncienService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new MandatAncienService();
      currentDate = new Date();
      elemDefault = new MandatAncien(123, 0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a MandatAncien', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a MandatAncien', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a MandatAncien', async () => {
        const returnedFromService = Object.assign(
          {
            idType: 1,
            libelle: 'BBBBBB',
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            circonscription: 'BBBBBB',
            libelleAffichage: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a MandatAncien', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a MandatAncien', async () => {
        const patchObject = Object.assign(
          {
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            circonscription: 'BBBBBB',
          },
          new MandatAncien()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a MandatAncien', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of MandatAncien', async () => {
        const returnedFromService = Object.assign(
          {
            idType: 1,
            libelle: 'BBBBBB',
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            circonscription: 'BBBBBB',
            libelleAffichage: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of MandatAncien', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a MandatAncien', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a MandatAncien', async () => {
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
