import React from 'react';
import { shallow } from 'enzyme';
import LoginPage from '../src/pages/LoginPage';
import en from '../src/i18n/en';
import helpers from './helpers';
import PageTitle from '../src/components/PageTitle';
import Button from '../src/components/Button';

describe('LoginView', () => {
  let wrapper;
  const location = {
    state: {
      referrer: '',
    },
  };
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<LoginPage location={location} />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });

  it('should render', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should contain a PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain Button-components', () => {
    expect(wrapper.find(Button).length).toBe(1);
  });

  it('should contain Link-component', () => {
    expect(wrapper.find('Link').length).toBe(1);
  });

  it('should contain ErrorList-component', () => {
    expect(wrapper.find('ErrorList').length).toBe(1);
  });

  describe('user enter an email-adress', () => {
    const value = 'sample@email.com';

    beforeEach(() => {
      const input = wrapper.find('input').first();


      input.simulate('change', {
        target: {
          value,
          name: 'email',
        },
      });
    });

    it("should update state 'field.email'", () => {
      expect(wrapper.state().fields.email).toBe(value);
    });

    describe('user clicks login', () => {
      beforeEach(() => {
        const btn = wrapper.find('input[type="submit"]').first();

        btn.simulate('click', {
          preventDefault: () => {},
        });
      });

      it('should add error to state fieldErrors', () => {
        expect(wrapper.state().fieldErrors.length).toBe(1);
      });
    });

    describe('user enter a long password', () => {
      const longPassword = helpers.generateString(300);
      const input = wrapper.find('input').at(2);

      beforeEach(() => {
        input.simulate('change', {
          target: {
            value: longPassword,
            name: 'password',
          },
        });
      });

      it('should add error to state fieldErrors', () => {
        expect(wrapper.state().fieldErrors.length).toBe(1);
      });
    });

    describe('user enters a valid password', () => {
      describe('user entera a invalid email', () => {
        it('should add error to state fieldErrors', () => {
          expect(wrapper.state().fieldErrors.length).toBe(1);
        });
        describe('user enters a too short password', () => {
          it('UI should display 2 errors', () => {
            expect(wrapper.state().fieldErrors.length).toBe(1);
          });
          it('should add error to state fieldErrors', () => {
            expect(wrapper.state().fieldErrors.length).toBe(1);
          });
          it("should update state 'field.password'", () => {
            // Body...
          });
        });
      });

      describe('user clicks login', () => {
        it('state loginInProgress should be set to true', () => {
          // Body...
        });

        describe('login is successfull', () => {
          it('state shouldRedirect should be set to true', () => {
            // Body...
          });
          it('state fields should be empty', () => {
            // Body...
          });
        });
        describe('login is not successfull', () => {
          it('state loginInProgress should be set to false', () => {
            // Body...
          });
          it('should add error to fieldErrors', () => {
            // Body...
          });
        });
      });
    });
  });
});
