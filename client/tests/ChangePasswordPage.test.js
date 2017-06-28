import React from 'react';
import { render } from 'enzyme';
import ChangePasswordPage from '../src/pages/ChangePasswordPage';
import ErrorList from '../src/components/ErrorList';
import en from '../src/i18n/en';
import PageTitle from '../src/components/PageTitle';
import Button from '../src/components/Button';

describe('ChangePasswordPage', () => {
  let wrapper;
  const context = { language: en };
  const id = '12345';
  const Client = {
    post: jest.fn(),
  };

  beforeEach(() => {
    // wrapper = render(<ChangePasswordPage id={id} />, { context });
  });

  it('should be defined', () => {
    // expect(wrapper).toBeDefined();
  });
});
/*
  it('should be rendered', () => {
    expect(wrapper.find('div').length).toBeGreaterThan(0);
  });

  it('should contain a PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain Button-components', () => {
    expect(wrapper.find(Button).length).toBe(1);
  });

  it('should render 2 input', () => {
    expect(wrapper.find({ type: 'password' }).length).toBe(2);
  });

  it('should render a button', () => {
    expect(wrapper.find({ type: 'submit' }).length).toBe(1);
  });

  it("should store props 'id' in state 'fields.urlId'", () => {
    expect(wrapper.state().fields.urlId).toBe(id);
  });

  describe('user clicks save without entering a password', () => {
    beforeEach(() => {
      wrapper = mount(<ChangePasswordPage id={id} />, { context });
      const form = wrapper.find('form').first();
      form.simulate('submit', {
        preventDefault: () => {},
      });
    });

    it("should update state 'fieldErrors' with password is missing error", () => {
      expect(wrapper.state().fieldErrors.length).toBe(2);
    });

    it("should pass state 'fieldErrors' as prop to ErrorList", () => {
      const fieldErrors = wrapper.state().fieldErrors;
      const errorList = wrapper.find('ErrorList').first();
      expect(errorList.props().errors).toBe(fieldErrors);
    });

    it('should contain <ErrorList/> component', () => {
      expect(wrapper.find(ErrorList).length).toBe(1);
    });
  });

  describe('user enters too short password', () => {
    const value = 'pass';

    beforeEach(() => {
      wrapper = mount(<ChangePasswordPage id={id} />, { context });
      const pass1 = wrapper.find('input').at(1);
      const pass2 = wrapper.find('input').at(2);

      pass1.simulate('change', {
        target: {
          value,
          name: 'password',
        },
      });
      pass2.simulate('change', {
        target: {
          value,
          name: 'passwordConfirmation',
        },
      });
    });

    it("should update state 'fields.password'", () => {
      expect(wrapper.state().fields.password).toBe(value);
    });

    it("should update state 'fields.passwordConfirmation'", () => {
      expect(wrapper.state().fields.passwordConfirmation).toBe(value);
    });

    describe('user clicks save', () => {
      beforeEach(() => {
        wrapper = mount(<ChangePasswordPage id={id} />, { context });
        const form = wrapper.find('form').first();
        form.simulate('submit', {
          preventDefault: () => {},
        });
      });

      it("should update state 'fieldErrors' with password too short error", () => {
        expect(wrapper.state().fieldErrors.length).toBe(1);
      });
    });
  });
});
*/
