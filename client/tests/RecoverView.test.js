import React from 'react';
import { shallow } from 'enzyme';
import RecoverView from '../src/components/RecoverView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';
import Button from '../src/components/Button';

describe('RecoverView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<RecoverView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain Button-components', () => {
    expect(wrapper.find(Button).length).toBe(1);
  });
});
