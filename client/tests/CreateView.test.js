import React from 'react';
import { shallow } from 'enzyme';
import CreateView from '../src/components/CreateView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';

describe('CreateView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<CreateView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });
});
