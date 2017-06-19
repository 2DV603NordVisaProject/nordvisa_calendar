import React from 'react';
import { shallow } from 'enzyme';
import PendingRegistrationsView from '../src/components/PendingRegistrationsView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';
import Button from '../src/components/Button';

describe('PendingRegistrationView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<PendingRegistrationsView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain Button-components', () => {
    expect(wrapper.find(Button).length).toBe(1);
  });
});
