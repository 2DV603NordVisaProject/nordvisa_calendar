import React from 'react';
import { shallow } from 'enzyme';
import RegisterPage from '../src/pages/RegisterPage';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';
import RegisterForm from '../src/components/RegisterForm';

describe('RegisterPage', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<RegisterPage />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain RegisterForm-components', () => {
    expect(wrapper.find(RegisterForm).length).toBe(1);
  });
});
