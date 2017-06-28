import React from 'react';
import { shallow } from 'enzyme';
import UpdateAccount from '../src/components/UpdateAccount';
import en from '../src/i18n/en';
import Button from '../src/components/Button';

describe('UpdateAccount', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<UpdateAccount />, { context });
  });

  it('should contain Button-components', () => {
    expect(wrapper.find(Button).length).toBe(1);
  });
});
