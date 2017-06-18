import React from 'react';
import { shallow } from 'enzyme';
import RegisterView from '../src/components/RegisterView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';

describe('RegisterView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<RegisterView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });
});
