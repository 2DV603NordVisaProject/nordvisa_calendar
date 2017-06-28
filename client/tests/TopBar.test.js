import React from 'react';
import { shallow } from 'enzyme';
import TopBar from '../src/components/TopBar';
import LanguageSelect from '../src/components/LanguageSelect';

describe('TopBar', () => {
  let wrapper;
  const context = { language: {
    TopBar: '',
    swedish: '',
    english: '',
    danish: '',
    icelandic: '',
    norwegian: '',
  } };
  const onLanguageChange = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<TopBar onLanguageChange={onLanguageChange} />, { context });
  });

  it('should render TopBar', () => {
    expect(wrapper.find('.topbar').length).toBe(1);
  });

  it('should render brand', () => {
    expect(wrapper.find('.brand').length).toBe(1);
  });

  it('should contain LanguageSelect', () => {
    expect(wrapper.find(LanguageSelect).length).toBe(1);
  });
});
