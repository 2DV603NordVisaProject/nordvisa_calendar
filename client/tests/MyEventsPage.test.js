import React from 'react';
import { shallow } from 'enzyme';
import MyEventsPage from '../src/pages/MyEventsPage';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';

describe('MyEventsPage', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<MyEventsPage />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });
});
