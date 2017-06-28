import React from 'react';
import { shallow } from 'enzyme';
import EventPage from '../src/pages/EventPage';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';
import Button from '../src/components/Button';

describe('EventPage', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<EventPage />, { context });
  });

  it('should be defined', () => {
    expect(wrapper).toBeDefined();
  });
});
