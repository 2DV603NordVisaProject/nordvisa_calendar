import React from 'react';
import { shallow } from 'enzyme';
import MembersView from '../src/components/MembersView';
import PageTitle from '../src/components/PageTitle';
import en from '../src/i18n/en';
import Button from '../src/components/Button';

describe('MembersView', () => {
  let wrapper;
  const context = { language: en };

  beforeEach(() => {
    wrapper = shallow(<MembersView />, { context });
  });

  it('should contain PageTitle-component', () => {
    expect(wrapper.find(PageTitle).length).toBe(1);
  });

  it('should contain Button-components', () => {
    expect(wrapper.find(Button).length).toBe(1);
  });
});
