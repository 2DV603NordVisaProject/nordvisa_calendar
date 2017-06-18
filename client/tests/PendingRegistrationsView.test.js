import React from 'react';
import { shallow } from 'enzyme';
import PendingRegistrationsView from '../src/components/PendingRegistrationsView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';

describe('PendingRegistrationView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<PendingRegistrationsView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });
});
