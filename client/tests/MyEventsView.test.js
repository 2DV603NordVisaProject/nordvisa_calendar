import React from 'react';
import { shallow } from 'enzyme';
import MyEventsView from '../src/components/MyEventsView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';

describe('MyEventsView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<MyEventsView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });
});
